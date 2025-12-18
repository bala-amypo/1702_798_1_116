@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(
                user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public String login() {
        return "JWT_TOKEN_GENERATED_HERE";
    }
}
