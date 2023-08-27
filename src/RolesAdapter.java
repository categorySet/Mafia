public class RolesAdapter {

    private Roles roles;
    private String rolesName;

    public RolesAdapter(final Roles roles) {
        this.roles = roles;
        this.roles.toString();
    }

    public void useAbllitity(String name) {
        if (roles instanceof Mafia) {
            ((Mafia) roles).kill(name);
        } else if (roles instanceof Doctor) {
            ((Doctor) roles).save(name);
        } else if (roles instanceof Police) {
            ((Police) roles).scan(name);
        }
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(final Roles roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return roles.toString();
    }
}
