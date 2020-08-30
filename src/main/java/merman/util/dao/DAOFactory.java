package merman.util.dao;

import java.sql.Connection;
import java.sql.SQLException;
import merman.util.dao.exception.DAOConfigurationException;
import merman.util.dao.implementation.BanksDAOImpl;
import merman.util.dao.implementation.ConsumableInvoicesDAOImpl;
import merman.util.dao.implementation.ConsumableInvoicesProductsDAOImpl;
import merman.util.dao.implementation.ContractorTypesDAOImpl;
import merman.util.dao.implementation.ContractorsContractsDAOImpl;
import merman.util.dao.implementation.ContractorsDAOImpl;
import merman.util.dao.implementation.CurrenciesDAOImpl;
import merman.util.dao.implementation.DiscountsDAOImpl;
import merman.util.dao.implementation.EmployeesDAOImpl;
import merman.util.dao.implementation.FirmsBankAccountsDAOImpl;
import merman.util.dao.implementation.FirmsDAOImpl;
import merman.util.dao.implementation.FlipContainersDAOImpl;
import merman.util.dao.implementation.PositionsDAOImpl;
import merman.util.dao.implementation.ProductCategoriesDAOImpl;
import merman.util.dao.implementation.ProductsDAOImpl;
import merman.util.dao.implementation.ProductsPricesDAOImpl;
import merman.util.dao.implementation.RightsDAOImpl;
import merman.util.dao.implementation.TypesOfChargesDAOImpl;
import merman.util.dao.implementation.TypesOfPricesDAOImpl;
import merman.util.dao.implementation.UnitsDAOImpl;
import merman.util.dao.implementation.UsersDAOImpl;
import merman.util.dao.implementation.VATRatesDAOImpl;
import merman.util.dao.implementation.WarehousesDAOImpl;
import merman.util.dao.interfaces.BanksDAO;
import merman.util.dao.interfaces.ConsumableInvoicesDAO;
import merman.util.dao.interfaces.ConsumableInvoicesProductsDAO;
import merman.util.dao.interfaces.ContractorTypesDAO;
import merman.util.dao.interfaces.ContractorsContractsDAO;
import merman.util.dao.interfaces.ContractorsDAO;
import merman.util.dao.interfaces.CurrenciesDAO;
import merman.util.dao.interfaces.DiscountsDAO;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.dao.interfaces.FirmsBankAccountsDAO;
import merman.util.dao.interfaces.FirmsDAO;
import merman.util.dao.interfaces.FlipContainersDAO;
import merman.util.dao.interfaces.PositionsDAO;
import merman.util.dao.interfaces.ProductCategoriesDAO;
import merman.util.dao.interfaces.ProductsDAO;
import merman.util.dao.interfaces.ProductsPricesDAO;
import merman.util.dao.interfaces.RightsDAO;
import merman.util.dao.interfaces.TypesOfChargesDAO;
import merman.util.dao.interfaces.TypesOfPricesDAO;
import merman.util.dao.interfaces.UnitsDAO;
import merman.util.dao.interfaces.UsersDAO;
import merman.util.dao.interfaces.VATRatesDAO;
import merman.util.dao.interfaces.WarehousesDAO;

/**
 * @author Arthur Sadykov
 */
public abstract class DAOFactory {

    private static final String PROPERTY_URL = "url1";

    public static DAOFactory getInstance(DatabaseName databaseName, String username, String password) {
        if (databaseName == null) {
            throw new DAOConfigurationException("Не предоставлен URL базы данных.");
        }
        DAOProperties properties = new DAOProperties(databaseName.toString());
        String url = properties.getProperty(PROPERTY_URL, true);
        return new DriverManagerDAOFactory(url, username, password);
    }

    public abstract Connection getConnection() throws SQLException;

    public ContractorsDAO getContractorsDAO() {
        return new ContractorsDAOImpl(this);
    }

    public ContractorTypesDAO getContractorTypesDAO() {
        return new ContractorTypesDAOImpl(this);
    }

    public ProductsDAO getProductsDAO() {
        return new ProductsDAOImpl(this);
    }

    public ProductCategoriesDAO getProductCategoriesDAO() {
        return new ProductCategoriesDAOImpl(this);
    }

    public UnitsDAO getUnitsDAO() {
        return new UnitsDAOImpl(this);
    }

    public TypesOfPricesDAO getTypesOfPricesDAO() {
        return new TypesOfPricesDAOImpl(this);
    }

    public VATRatesDAO getVATRatesDAO() {
        return new VATRatesDAOImpl(this);
    }

    public RightsDAO getRightsDAO() {
        return new RightsDAOImpl(this);
    }

    public DiscountsDAO getDiscountsDAO() {
        return new DiscountsDAOImpl(this);
    }

    public EmployeesDAO getEmployeesDAO() {
        return new EmployeesDAOImpl(this);
    }

    public PositionsDAO getPositionsDAO() {
        return new PositionsDAOImpl(this);
    }

    public FlipContainersDAO getFlipContainersDAO() {
        return new FlipContainersDAOImpl(this);
    }

    public UsersDAO getUsersDAO() {
        return new UsersDAOImpl(this);
    }

    public TypesOfChargesDAO getTypesOfChargesDAO() {
        return new TypesOfChargesDAOImpl(this);
    }

    public WarehousesDAO getWarehousesDAO() {
        return new WarehousesDAOImpl(this);
    }

    public ProductsPricesDAO getProductsPricesDAO() {
        return new ProductsPricesDAOImpl(this);
    }

    public ContractorsContractsDAO getContractorsContractsDAO() {
        return new ContractorsContractsDAOImpl(this);
    }

    public ConsumableInvoicesProductsDAO getConsumableInvoicesProductsDAO() {
        return new ConsumableInvoicesProductsDAOImpl(this);
    }

    public ConsumableInvoicesDAO getConsumableInvoicesDAO() {
        return new ConsumableInvoicesDAOImpl(this);
    }

    public FirmsDAO getFirmsDAO() {
        return new FirmsDAOImpl(this);
    }

    public CurrenciesDAO getCurrenciesDAO() {
        return new CurrenciesDAOImpl(this);
    }

    public BanksDAO getBanksDAO() {
        return new BanksDAOImpl(this);
    }

    public FirmsBankAccountsDAO getFirmsBankAccountsDAO() {
        return new FirmsBankAccountsDAOImpl(this);
    }
}
