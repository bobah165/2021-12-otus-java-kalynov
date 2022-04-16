package ru.otus.core.sessionmanager;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.concurrent.Callable;

public class TransactionManagerHibernate implements TransactionManager {
    private final SessionFactory sessionFactory;
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    public TransactionManagerHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> T doInTransaction(TransactionAction<T> action) {
        return doInTransaction(action, false);
    }

    @Override
    public <T> T doInReadOnlyTransaction(TransactionAction<T> action) {
        return doInTransaction(action, true);
    }


    private  <T> T doInTransaction(TransactionAction<T> action, boolean readOnlyTran) {
        return wrapException(() -> {
            try (var session = sessionFactory.openSession()) {
                log.info("open session: {}",session.getStatistics());
                if (readOnlyTran) {
                    session.setDefaultReadOnly(true);
                }
                var transaction = session.beginTransaction();
                try {
                    var result = action.apply(session);
                    session.flush();
                    transaction.commit();
                    session.clear();
                    log.info("close session: {}",session.getStatistics());
                    return result;
                } catch (Exception ex) {
                    transaction.rollback();
                    throw ex;
                }
            }
        });
    }

    private <T> T wrapException(Callable<T> action) {
        try {
            return action.call();
        } catch (Exception ex) {
            throw new DataBaseOperationException("Exception in transaction", ex);
        }
    }
}
