public class PlayerResources
{
    //integers for each type of resource
    private int Wood;
    private int Glass;
    private int Iron;
    private int Oil;
    private int People;
    private int Food;

    //initial player resources
    public PlayerResources() {
        Wood = 3;
        Glass = 3;
        Iron = 3;
        Oil = 3;
        People = 3;
        Food = 3;
    }

    //getters and setters for resources
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

    //actual cost utilization of resources
    public boolean useResourcesForBuilding(BuildingType bt) {
        if (bt == BuildingType.GlassFurnace) {
            if (this.Iron <= 0 || this.People <= 0)
            {
                return false;
            }
            else{
                this.Iron -= 1;
                this.People -= 1;

                return true;
            }
        }
        else if(bt == BuildingType.LumberMill){
            if (this.Iron <= 0 || this.People <= 0)
            {
                return false;
            }
            else{
                this.Iron -= 1;
                this.People -= 1;

                return true;
            }
        }
        else if(bt == BuildingType.Mine)
        {
            if (this.Wood <= 0 || this.People <= 0)
            {
                return false;
            }
            else {
            this.Wood -= 1;
            this.People -= 1;

            return true;
        }
        }
        else if(bt == BuildingType.Farm)
        {
            if (this.People <= 0){
                return false;
            }
        
        else{
            this.People -= 1;
            return true;
        }
        }
        else if(bt == BuildingType.OilDrill)
        {
            if (this.People <= 0 || this.Iron <= 0 ){
                return false;
            }
        	else{
        		this.People -= 1;
        		this.Iron -= 1;
        		return true;
        	}
       }
        else if(bt == BuildingType.House)
        {
        	if (this.Food <= 0 || this.Iron <= 1 || this.Wood <= 1 || this.Glass <= 1)
        	{
        		return false;
        	}
        	else
        	{
        		this.Food -= 1;
        		this.Iron -= 1;
        		this.Wood -= 1;
        		this.Glass -= 1;
        		return true;
        	}
        }
        else {
        	return false;
        }
    }

    public void assignResources(String resource)
    {
        String tileResources = resource.substring(0, resource.indexOf(", "));
        int amount = Integer.parseInt(resource.substring(resource.indexOf(", ") + 2));

        if (tileResources == "People")
        {
            this.People += amount;
        }
        else if (tileResources == "Food")
        {
            this.Food += amount;
        }
        else if (tileResources == "Wood")
        {
            this.Wood += amount;
        }
        else if (tileResources == "Glass")
        {
            this.Glass += amount;
        }
        else if (tileResources == "Iron")
        {
            this.Iron += amount;
        }
        else if (tileResources == "Oil")
        {
            this.Oil += amount;
        }
    }
}