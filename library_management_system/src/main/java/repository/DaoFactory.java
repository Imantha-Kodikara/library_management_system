package repository;

import repository.custom.BookRepository;
import repository.custom.impl.BookRepositoryImpl;
import service.custom.impl.BookServiceImpl;
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
        }
        return null;
    }
}
