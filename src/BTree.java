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

    // given an integer, returns the depth of the node in a binary tree
    // returns -1 if no node contains the given value
    public int depth(int n){
        // we will start at the root node and work our way down
        // to try to transverse the tree find the value we are looking for by looking at
        // the current node and going left or right by comparing the
        // given integer n and the current node's value
        BNode node = root;
        int depth = 0;
        while(node.getVal()!=n){
            if(n<node.getVal()){
                node = node.getLeft();
                depth++;
            }
            else if(n>node.getVal()){
                node = node.getRight();
                depth++;
            }
            if(node==null){
                return -1;
            }
        }
        return depth;
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