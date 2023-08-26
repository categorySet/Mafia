public enum Role {

    Mafia(0, "Mafia"),
    Citizen(1, "Citizen"),
    Doctor(2, "Doctor"),
    Police(3, "Police");

    private int roleNum;
    private String roleName;

    Role(int roleNum, String roleName) {
        this.roleNum = roleNum;
        this.roleName = roleName;
    }

    public int getRoleNum() {
        return roleNum;
    }

    public String getRoleName() {
        return roleName;
    }

    public static Role getByRoleNum(int roleNum) {
        for (Role role : Role.values()) {
            if (role.getRoleNum() == roleNum) {
                return role;
            }
        }

        return null;
    }

}
