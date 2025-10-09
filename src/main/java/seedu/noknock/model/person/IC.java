
package seedu.noknock.model.person;

/**
 * IC model
 */
public class IC {
    public final String ic;

    public IC(String ic) {
        this.ic = ic;
    }

    @Override
    public String toString() {
        return ic;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IC)) {
            return false;
        }

        IC otherIC = (IC) other;
        return ic.equals(otherIC.ic);
    }
}