package org.eternity.base.jpa.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.jdbc.mutation.group.PreparedStatementDetails;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.PostInsertIdentityPersister;
import org.hibernate.id.insert.Binder;
import org.hibernate.id.insert.IdentifierGeneratingInsert;
import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;
import org.hibernate.jdbc.Expectation;
import org.hibernate.metamodel.mapping.BasicEntityIdentifierMapping;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.model.ast.builder.TableInsertBuilder;
import org.hibernate.type.Type;

import java.sql.PreparedStatement;
import java.util.Properties;

public class LongIdClassGenerator extends IdentityGenerator implements Configurable {
    private Class clazz;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.clazz = type.getReturnedClass();
    }

    @Override
    public InsertGeneratedIdentifierDelegate getGeneratedIdentifierDelegate(PostInsertIdentityPersister persister) {
        InsertGeneratedIdentifierDelegate delegate = super.getGeneratedIdentifierDelegate(persister);
        return new InsertGeneratedIdentifierDelegate() {
            @Override
            public TableInsertBuilder createTableInsertBuilder(BasicEntityIdentifierMapping identifierMapping, Expectation expectation, SessionFactoryImplementor sessionFactory) {
                return delegate.createTableInsertBuilder(identifierMapping, expectation, sessionFactory);
            }

            @Override
            public PreparedStatement prepareStatement(String insertSql, SharedSessionContractImplementor session) {
                return delegate.prepareStatement(insertSql, session);
            }

            @Override
            public Object performInsert(PreparedStatementDetails insertStatementDetails, JdbcValueBindings valueBindings, Object entity, SharedSessionContractImplementor session) {
                return convert(delegate.performInsert(insertStatementDetails, valueBindings, entity, session));
            }

            @Override
            public IdentifierGeneratingInsert prepareIdentifierGeneratingInsert(SqlStringGenerationContext context) {
                return delegate.prepareIdentifierGeneratingInsert(context);
            }

            @Override
            public Object performInsert(String insertSQL, SharedSessionContractImplementor session, Binder binder) {
                return convert(delegate.performInsert(insertSQL, session, binder));
            }

            private Object convert(Object value) {
                try {
                    return clazz.getDeclaredConstructor(Long.class).newInstance(value);
                } catch (Exception e) {
                    throw new HibernateException(e);
                }
            }
        };
    }
}

/**
 * Hibernate 5.X
 * package org.eternity.food.domain.order;
 *
 * import org.hibernate.HibernateException;
 * import org.hibernate.MappingException;
 * import org.hibernate.dialect.Dialect;
 * import org.hibernate.engine.spi.SharedSessionContractImplementor;
 * import org.hibernate.id.*;
 * import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;
 * import org.hibernate.internal.util.config.ConfigurationHelper;
 * import org.hibernate.service.ServiceRegistry;
 * import org.hibernate.type.LongType;
 * import org.hibernate.type.Type;
 *
 * import javax.persistence.Id;
 * import java.io.Serializable;
 * import java.lang.reflect.InvocationTargetException;
 * import java.lang.reflect.TypeVariable;
 * import java.sql.ResultSet;
 * import java.sql.SQLException;
 * import java.util.Properties;
 *
 * public class IdClassGenerator extends AbstractPostInsertGenerator implements Configurable {
 *     public static final String ID_CLASS_NAME = "idClass";
 *
 *     private IdentityGenerator identityGenerator = new IdentityGenerator();
 *
 *     private Class clazz;
 *
 *     @Override
 *     public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
 * //        java.lang.reflect.Type[] val = type.getReturnedClass().getGenericInterfaces();
 * //        System.out.println(val);
 *     }
 *
 *     @Override
 *     public InsertGeneratedIdentifierDelegate getInsertGeneratedIdentifierDelegate(PostInsertIdentityPersister persister, Dialect dialect, boolean isGetGeneratedKeysEnabled) throws HibernateException {
 *         return new IdentityGenerator.BasicDelegate(persister, dialect) {
 *             @Override
 *             protected Serializable getResult(
 *                     SharedSessionContractImplementor session,
 *                     ResultSet rs,
 *                     Object object) throws SQLException {
 *                 Long value = (Long)IdentifierGeneratorHelper.getGeneratedIdentity(
 *                         rs,
 *                         persister.getRootTableKeyColumnNames()[0],
 *                         LongType.INSTANCE,
 *                         session.getJdbcServices().getJdbcEnvironment().getDialect()
 *                 );
 *
 *                 return new OrderId(value);
 *             }
 *         };
 *     }
 * }
 */
