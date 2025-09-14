class CoffeeMachine extends Machine{
    int bean;
    CoffeeMachine(String nm, boolean st, int cap){
        super(nm, st);
        this.bean = cap;
    }
    public void displayinfo(){
        System.out.println("Name: " + name + 
                            "\nStatus: " + status + 
                            "\nBean: " + bean);
    }

    public void callOperate(){
        if(bean > 0){
            System.out.println(this.name + " is brewing coffee..."
                                +"\nBean left: " + bean);
            bean--;
        } else {
            System.out.println("No bean available");
        }
    }


    @Override
    public void operate(){
        if(bean > 0){
        this.callOperate();
        }
        else{
            System.out.println("No bean available");
        }
    }

    public void multiplecoffee(int nofc){
        if(bean > 0){
            for(int i = bean; i > 0; i--){
                this.callOperate();
            }
        } 
        else {
            System.out.println("No bean available");
        }
    }
}