package pap.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pap.Pap;
import pap.controllers.UpdatableController;

public class LoadedPages {
    private LoadedPages() {}
    public record Page(Parent node, UpdatableController controller) {}
    private static Page makePage(String fxmlFilepath) {
        try {
            var loader = new FXMLLoader(Pap.class.getResource(fxmlFilepath));
            return new Page(loader.load(), loader.getController());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static final Page bookCreator = makePage("view/book-creator.fxml");
    public static final Page bookManager = makePage("view/book-manager.fxml");
    public static final Page bookReport = makePage("view/book-report.fxml");
    public static final Page bookView = makePage("view/book-view.fxml");
    public static final Page branchManage = makePage("view/branch-manage.fxml");
    public static final Page browseBookHistory = makePage("view/browse-book-history.fxml");
    public static final Page browseBranchEmployees = makePage("view/browse-branch-employees.fxml");
    public static final Page browseBranches = makePage("view/browse-branches.fxml");
    public static final Page browseCatalog = makePage("view/browse-catalog.fxml");
    public static final Page browseRental = makePage("view/browse-rental.fxml");
    public static final Page browseRentingHistory = makePage("view/browse-renting-history.fxml");
    public static final Page browseWishList = makePage("view/browse-wish-list.fxml");
    public static final Page employeeAccountCreate = makePage("view/employee-account-create.fxml");
    public static final Page employeeDashboard = makePage("view/employee-dashboard.fxml");
    public static final Page employeeIssueManage = makePage("view/employee-issue-manage.fxml");
    public static final Page employeeLoginScreen = makePage("view/employee-login-screen.fxml");
    public static final Page employeeManageBranches = makePage("view/employee-manage-branches.fxml");
    public static final Page employeeManageParameters = makePage("view/employee-manage-parameters.fxml");
    public static final Page employeePasswordChange = makePage("view/employee-password-change.fxml");
    public static final Page employeeShowIssue = makePage("view/employee-show-issue.fxml");
    public static final Page loginScreen = makePage("view/login-screen.fxml");
    public static final Page manageCatalog = makePage("view/manage-catalog.fxml");
    public static final Page userAccountCreate = makePage("view/user-account-create.fxml");
    public static final Page userAccountManage = makePage("view/user-account-manage.fxml");
    public static final Page userDashboard = makePage("view/user-dashboard.fxml");
    public static final Page userLoginScreen = makePage("view/user-login-screen.fxml");
    public static final Page browseQueues = makePage("view/browse-queues.fxml");
    public static final Page browsePenalties = makePage("view/browse-penalties.fxml");
}