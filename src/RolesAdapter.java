public class RolesAdapter {

    private Roles roles;
    private String rolesName;

    public RolesAdapter(final Roles roles) {
        this.roles = roles;
        this.roles.toString();
    }

    public String useAbllitity(String name) {
        if (roles instanceof Mafia) {
            if (!Mafia.killed) {
                Mafia.killed = true;
                ((Mafia) roles).kill(name);
            }
            return name + " 님을 살해합니다..";
        } else if (roles instanceof Doctor) {
            Doctor d = (Doctor) roles;

            if (!Doctor.saved) {
                Doctor.saved = true;
                d.save(name);
            }
            return name + " 님을 구하러갑니다..";
        } else if (roles instanceof Police) {
            Police p = (Police) roles;

            if (!Police.scaned) {
                Police.scaned = true;
                return name + " 님의 직업은" + p.scan(name) + "입니다.";
            }
        }

        return null;
    }

    public Roles getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return roles.toString();
    }
}
