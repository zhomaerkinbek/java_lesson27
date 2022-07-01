public class Cast extends Info{
    private String role;

    public Cast(String role, String fullName) {
        this.role = role;
        super.setFullName(fullName);
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format("%s, role: %s",getFullName(), role);
    }
}
