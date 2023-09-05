package model;

/** This record is a model of the User's data.
 * @param id The id to assign.
 * @param name The name to assign.
 * @param password The password to assign.
 */
public record User(int id, String name, String password) {

    /** Returns the name of the User.
     * @return The name to return.
     */
    @Override
    public String toString() {
        return name;
    }
}
