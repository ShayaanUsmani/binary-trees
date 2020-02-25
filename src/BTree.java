public class BTree{
    private BNode root;

    public BTree(){
        root = null;
    }

    public BNode getRoot(){return root;}

    public void add(int n){
        BNode tmp = new BNode(n);
        if(root==null){
            root = tmp;
        }
        else{
            add(root, tmp);
        }
    }

    public void add(BNode branch, BNode tmp){
        if(tmp.getVal() > branch.getVal()){
            if(branch.getRight()==null){
                branch.setRight(tmp);
            }
            else{
                add(branch.getRight(),tmp);
            }
        }
        else if(tmp.getVal() < branch.getVal()){
            if(branch.getLeft()==null){
                branch.setLeft(tmp);
            }
            else{
                add(branch.getLeft(), tmp);
            }
        }
    }

    @Override
    public String toString(){
        String ans = stringify(root);
        if(ans!=""){
            ans = ans.substring(0,ans.length()-2);
        }
        return "{"+ans+"}";
    }

    public String stringify(BNode branch){
        if(branch != null){
            return stringify(branch.getLeft()) +
                    branch.getVal() + ", " +
                    stringify(branch.getRight());
        }
        return "";
    }
}