public enum Role {

    Mafia(1, "Mafia"),
    Citizen(2, "Citizen"),
    Doctor(3, "Doctor"),
    Police(4, "Police");

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

}
