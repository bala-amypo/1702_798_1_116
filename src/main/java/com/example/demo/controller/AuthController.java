@PostMapping("/register")
public ResponseEntity<?> register(@RequestParam String email,
                                  @RequestParam String password) {

    return ResponseEntity.ok(userService.registerUser(email, password));
}
