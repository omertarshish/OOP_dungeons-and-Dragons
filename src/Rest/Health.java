package Rest;

public class Health {
    protected int healthPool;
    protected int healthAmount;

    public Health (int healthPool) {
        this.healthPool = healthPool;
        this.healthAmount = healthPool;
    }

    public void HealthLevelUp(int level) {
        this.healthPool += 10 * level;
        this.healthAmount = this.healthPool;
    }

    public int getHealthPool() {
        return healthPool;
    }
    public int getHealthAmount() {
        return healthAmount;
    }

    public void setHealthAmount(int healthAmount){
        this.healthAmount = healthAmount;
    }
    public void increaseHealthPool (int healthAdded) {
        this.healthPool += healthAdded;

    }


    public void increaseHealthAmount (int healthRestored) {
        if (healthAmount + healthRestored > healthPool) {
            this.healthAmount = healthPool;
        }
        else
            this.healthAmount += healthRestored;
    }
    public void decreaseHealth (int Damage) {
        this.healthAmount -= Damage;
        if (healthAmount < 0)
            healthAmount = 0;
    }

    public String toString() {
        return  healthAmount+"/"+healthPool;
    }


}
