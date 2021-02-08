import java.util.List;

public class AdversarialSearch implements SearchAlgorithm{
    @Override
    public void doSearch(Environment env) {}

    @Override
    public List<Action> getPlan() {
        return null;
    }

    private void findBestNodeInFrontier(){}

    private void expandNode(){}

    @Override
    public int getNbNodeExpansions() {
        return 0;
    }

    @Override
    public int getMaxFrontierSize() {
        return 0;
    }

    @Override
    public int getPlanCost() {
        return 0;
    }
}
