package repository;

import repository.custom.IssuedBookRepository;
import repository.custom.impl.BookRepositoryImpl;
import repository.custom.impl.IssuedBookRepositoryImpl;
import repository.custom.impl.MemberRepositoryImpl;
import util.RepositoryType;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance == null ? instance = new DaoFactory() : instance;
    }

    public<T extends SuperRepository> T getRepositoryType(RepositoryType type){
        switch (type){
            case BOOK: return (T) new BookRepositoryImpl();
            case MEMBER: return (T) new MemberRepositoryImpl();
            case ISSUED_BOOK: return (T) new IssuedBookRepositoryImpl();
        }
        return null;
    }
}
