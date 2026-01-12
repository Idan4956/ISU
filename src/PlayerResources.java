public class PlayerResources
{
    private int Wood;
    private int Glass;
    private int Iron;
    private int Oil;
    private int People;
    private int Food;

    public PlayerResources() {
        Wood = 3;
        Glass = 3;
        Iron = 3;
        Oil = 3;
        People = 3;
        Food = 3;
    }

    public int getGlass() {
        return Glass;
    }

    public void setGlass(int glass) {
        Glass = glass;
    }

    public int getIron() {
        return Iron;
    }

    public void setIron(int iron) {
        Iron = iron;
    }

    public int getOil() {
        return Oil;
    }

    public void setOil(int oil) {
        Oil = oil;
    }

    public int getPeople() {
        return People;
    }

    public void setPeople(int people) {
        People = people;
    }

    public int getFood() {
        return Food;
    }

    public void setFood(int food) {
        Food = food;
    }

    public int getWood() {
        return Wood;
    }

    public void setWood(int wood) {
        Wood = wood;
    }

    public boolean useResourcesForBuilding(BuildingType bt) {
        if (bt == BuildingType.GlassFurnace) {
            if (this.Iron <= 0 || this.People <= 0)
            {
                return false;
            }
            this.Iron = this.Iron - 1;
            this.People = this.People - 1;

            return true;
        }
        return false;
    }
}