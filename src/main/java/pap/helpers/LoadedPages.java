package pap.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pap.Pap;
import pap.controllers.UpdatableController;

public class LoadedPages {
    private LoadedPages() {}
    
    public static final Parent userDashboard;
    public static final UpdatableController userDashboardController;
    public static final Parent employeeDashboard;
    public static final UpdatableController employeeDashboardController;
    public static final Parent loginScreen;
    public static final UpdatableController loginScreenController;
    public static final UpdatableController userLoginScreenController;
    public static final Parent bookCreator;
    public static final UpdatableController bookCreatorController;
    public static final Parent bookManager;
    public static final UpdatableController bookManagerController;
    public static final Parent bookView;
    public static final UpdatableController bookViewController;
    public static final Parent browseCatalog;
    public static final UpdatableController browseCatalogController;
    public static final Parent manageCatalog;
    public static final UpdatableController manageCatalogController;
    public static final Parent employeeAccountCreate;
    public static final UpdatableController employeeAccountCreateController;
    public static final Parent employeeLoginScreen;
    public static final UpdatableController employeeLoginScreenController;
    public static final Parent userAccountCreate;
    public static final UpdatableController userAccountCreateController;
    public static final Parent userAccountManage;
    public static final UpdatableController userAccountManageController;
    public static final Parent employeePasswordChange;
    public static final UpdatableController employeePasswordChangeController;
    public static final Parent browseRental;
    public static final UpdatableController browseRentalController;
    public static final Parent browseHistory;
    public static final UpdatableController browseHistoryController;
    public static final Parent browseWishList;
    public static final UpdatableController browseWishListController;
    public static final Parent employeeManageParameters;
    public static final UpdatableController employeeManageParametersController;
    public static final Parent browseBookHistory;
    public static final UpdatableController browseBookHistoryController;
    public static final Parent browseBranches;
    public static final UpdatableController browseBranchesController;
    public static final Parent browseBranchEmployees;
    public static final UpdatableController browseBranchEmployeesController;
    public static final Parent employeeManageBranches;
    public static final UpdatableController employeeManageBranchesController;
    public static final Parent branchManager;
    public static final UpdatableController branchManagerController;
    
    static {
        try {
            var userDashboardLoader = new FXMLLoader(Pap.class.getResource("view/user-dashboard.fxml"));
            userDashboard = userDashboardLoader.load();
            userDashboardController = userDashboardLoader.getController();

            var employeeDashboardLoader = new FXMLLoader(Pap.class.getResource("view/employee-dashboard.fxml"));
            employeeDashboard = employeeDashboardLoader.load();
            employeeDashboardController = employeeDashboardLoader.getController();
            
            var loginScreenLoader = new FXMLLoader(Pap.class.getResource("view/login-screen.fxml"));
            loginScreen = loginScreenLoader.load();
            loginScreenController = loginScreenLoader.getController();
            
            var userLoginScreenLoader = new FXMLLoader(Pap.class.getResource("view/user-login-screen.fxml"));
            userLoginScreenController = userLoginScreenLoader.getController();
            
            var bookCreatorLoader = new FXMLLoader(Pap.class.getResource("view/book-creator.fxml"));
            bookCreator = bookCreatorLoader.load();
            bookCreatorController = bookCreatorLoader.getController();
            
            var bookManagerLoader = new FXMLLoader(Pap.class.getResource("view/book-manager.fxml"));
            bookManager = bookManagerLoader.load();
            bookManagerController = bookManagerLoader.getController();

            var bookViewLoader = new FXMLLoader(Pap.class.getResource("view/book-view.fxml"));
            bookView = bookViewLoader.load();
            bookViewController = bookViewLoader.getController();

            var browseCatalogLoader = new FXMLLoader(Pap.class.getResource("view/browse-catalog.fxml"));
            browseCatalog = browseCatalogLoader.load();
            browseCatalogController = browseCatalogLoader.getController();

            var manageCatalogLoader = new FXMLLoader(Pap.class.getResource("view/manage-catalog.fxml"));
            manageCatalog = manageCatalogLoader.load();
            manageCatalogController = manageCatalogLoader.getController();

            var employeeAccountCreateLoader = new FXMLLoader(Pap.class.getResource("view/employee-account-create.fxml"));
            employeeAccountCreate = employeeAccountCreateLoader.load();
            employeeAccountCreateController = employeeAccountCreateLoader.getController();

            var employeeLoginScreenLoader = new FXMLLoader(Pap.class.getResource("view/employee-login-screen.fxml"));
            employeeLoginScreen = employeeLoginScreenLoader.load();
            employeeLoginScreenController = employeeLoginScreenLoader.getController();

            var userAccountCreateLoader = new FXMLLoader(Pap.class.getResource("view/user-account-create.fxml"));
            userAccountCreate = userAccountCreateLoader.load();
            userAccountCreateController = userAccountCreateLoader.getController();

            var userAccountManageLoader = new FXMLLoader(Pap.class.getResource("view/user-account-manage.fxml"));
            userAccountManage = userAccountManageLoader.load();
            userAccountManageController = userAccountManageLoader.getController();
            
            var employeePasswordChangeLoader = new FXMLLoader(Pap.class.getResource("view/employee-password-change.fxml"));
            employeePasswordChange = employeePasswordChangeLoader.load();
            employeePasswordChangeController = employeePasswordChangeLoader.getController();
            
            var browseRentalLoader = new FXMLLoader(Pap.class.getResource("view/browse-rental.fxml"));
            browseRental = browseRentalLoader.load();
            browseRentalController = browseRentalLoader.getController();
            
            var browseHistoryLoader = new FXMLLoader(Pap.class.getResource("view/browse-renting-history.fxml"));
            browseHistory = browseHistoryLoader.load();
            browseHistoryController = browseHistoryLoader.getController();

            var browseWishListLoader = new FXMLLoader(Pap.class.getResource("view/browse-wish-list.fxml"));
            browseWishList = browseWishListLoader.load();
            browseWishListController = browseWishListLoader.getController();
            
            var manageParametersLoader = new FXMLLoader(Pap.class.getResource("view/employee-manage-parameters.fxml"));
            employeeManageParameters = manageParametersLoader.load();
            employeeManageParametersController = manageParametersLoader.getController();

            var browseBookHistoryLoader = new FXMLLoader(Pap.class.getResource("view/browse-book-history.fxml"));
            browseBookHistory = browseBookHistoryLoader.load();
            browseBookHistoryController = browseBookHistoryLoader.getController();

            var browseBranchesLoader = new FXMLLoader(Pap.class.getResource("view/browse-branches.fxml"));
            browseBranches = browseBranchesLoader.load();
            browseBranchesController = browseBranchesLoader.getController();

            var browseBranchEmployeesLoader = new FXMLLoader(Pap.class.getResource("view/browse-branch-employees.fxml"));
            browseBranchEmployees = browseBranchEmployeesLoader.load();
            browseBranchEmployeesController = browseBranchEmployeesLoader.getController();

            var employeeManageBranchesLoader = new FXMLLoader(Pap.class.getResource("view/employee-manage-branches.fxml"));
            employeeManageBranches = employeeManageBranchesLoader.load();
            employeeManageBranchesController = employeeManageBranchesLoader.getController();

            var branchManagerLoader = new FXMLLoader(Pap.class.getResource("view/branch-manage.fxml"));
            branchManager = branchManagerLoader.load();
            branchManagerController = branchManagerLoader.getController();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}