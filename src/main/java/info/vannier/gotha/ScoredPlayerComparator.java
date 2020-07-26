package info.vannier.gotha;

import ru.gofederation.gotha.model.PlacementCriterion;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * implements comparison methods between 2 ScoredPlayer
 * according to criteria.
 * if bKeepExAequo == true, comparison is made on placement criteria only
 * if bKeepExAequo == false, exaequo are then compared according alphabetical order of name and first name
 **/
class ScoredPlayerComparator implements Comparator<ScoredPlayer>, Serializable{
    private final PlacementCriterion[] criterion;
    private final int roundNumber;
    private final boolean bKeepExAequo;

    public ScoredPlayerComparator(PlacementCriterion[] crit, int roundNumber, boolean bKeepExAequo) {
        criterion = Arrays.copyOf(crit, crit.length);
        this.roundNumber = roundNumber;
        this.bKeepExAequo = bKeepExAequo;
    }

    @Override
    public int compare(ScoredPlayer sP1, ScoredPlayer sP2) {
        int result = betterByScore(sP1, sP2);
        if (result != 0) return result;

        if (bKeepExAequo) return 0;

        if (sP1.getName().compareTo(sP2.getName()) > 0) return 1;
        else if (sP1.getName().compareTo(sP2.getName()) < 0) return -1;
        if (sP1.getFirstName().compareTo(sP2.getFirstName()) > 0) return 1;
        else if (sP1.getFirstName().compareTo(sP2.getFirstName()) < 0) return -1;

        return 0;
    }

    public int betterByScore(ScoredPlayer sP1, ScoredPlayer sP2) {
        for (int cr = 0; cr < criterion.length; cr++){
            if (sP1.getCritValue(criterion[cr], roundNumber) < sP2.getCritValue(criterion[cr], roundNumber)) return 1;
            else if (sP1.getCritValue(criterion[cr], roundNumber) > sP2.getCritValue(criterion[cr], roundNumber)) return -1;
        }
        return 0;
    }
}
