package application;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // Login method: Validates user and returns user data
    public UserDTO login(String email, String password) throws Exception {
        return userDAO.authenticateUser(email, password);
    }

    // Signup method: Registers only students
    public boolean signup(String username, String email, String password) throws Exception {
        if (userDAO.checkUserExists(email)) {
            throw new Exception("User already exists with this email.");
        }
        return userDAO.createStudent(username, email, password);
    }
}
