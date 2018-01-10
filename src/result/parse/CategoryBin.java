package result.parse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author phantom
 */
public class CategoryBin {
    private Set<String> set = new HashSet<>();
    public CategoryBin() {
    }

    public void add (String element){set.add(element);}

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }
}
