public class BTree{
    private BNode root;

    public BTree(){
        root = null;
    }

    private static final int IN = 0;
    private static final int PRE = 1;
    private static final int POST = 2;

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

    public void display(){
        System.out.println(this);
    }

    // given integers 0,1, or 2, prints the binary tree's data in, pre, or post-order.
    public void display(int o){
        if(root==null){
            return;
        }
        if(o==IN){
            display();
        }
        else if(o==PRE){
            System.out.print("{");
            displayPre(root);
            System.out.println("}");
        }
        else if(o==POST){
            System.out.print("{");
            displayPost(root);
            System.out.println("}");
        }
        else{
            System.out.println("invalid input - please pick integers 0-2 (inclusive) indicating display being in, pre, or post-order respectively");
        }

    }

    // Prints binary tree's data in post-order (l,r,n)
    // Knowing the pattern is left,right,node, we can
    // find the left, then right child of a node, printing
    // the current node we are on while transversing, the base
    // case being we have gone past a leaf
    private void displayPost(BNode node){
        if(node == null){
            return;
        }
        displayPost(node.getLeft());
        displayPost(node.getRight());
        System.out.print(node.getVal());
        if(node!=root){
            System.out.print(", ");
        }
    }

    // Prints binary tree's data in pre-order (n,l,r)
    private void displayPre(BNode node){
        if(node == null){
            return;
        }
        if(node!=root){
            System.out.print(", ");
        }
        System.out.print(node.getVal());
        displayPre(node.getLeft());
        displayPre(node.getRight());
    }

    public int countLeaves(){
        return countLeaves(root);
    }

    // To find the total leaves of a binary tree (or a subtree), we
    // start at the root node of the tree and if the left and right nodes
    // of the node are null, it means it is a leaf so we return 1. Otherwise
    // we return the leaf count of the left and the right tree meaning
    // we will recursively return all of the leaves counted in the end
    private int countLeaves(BNode node){
        if(node==null){
            return 0;
        }
        if(node.getRight()==null && node.getLeft()==null){
            return 1;
        }
        else{
            return countLeaves(node.getLeft()) + countLeaves(node.getRight());
        }
    }

    public int height(){
        return height(root, 0);
    }

    // To find the height of the tree or sub tree, we can start
    // at the top of the tree, if the node we are on is null then
    // we have reached a leaf. We basically go to each leaf and compare
    // the depth reached, compare and return the higher depth.
    private int height(BNode node, int currH){
        if(node==null){
            return currH;
        }
        currH++;
        if(height(node.getLeft(), currH)<=height(node.getRight(), currH)){
            return height(node.getRight(), currH);
        }
        else{
            return height(node.getLeft(), currH);
        }
    }

    // takes two integers and tells if the first is an
    // ancestor of the second by first finding node
    // containing a and then trying to find the node
    // containing b underneath it.
    public void isAncestor(int a, int b){
        if(a!=b && findNode(findNode(root,a),b)!=null){
            System.out.println("a is ancestor of b");
        }
        else{
            System.out.println("a is not ancestor b");
        }
    }

    public void delete(int n){
        BNode node = findNode(root, n);
        // if the node is a leaf
        if(node.getLeft()==null && node.getRight()==null){
            node=null;
        }
        // if the node only has a right child
        else if(node.getLeft()==null){
            BNode temp = node.getRight();
            node.setRight(null);
            node = temp;
        }
        // if the node only has a left child
        else if(node.getRight()==null){
            BNode temp = node.getLeft();
            node.setLeft(null);
            node = temp;
        }
        else{
            BNode temp = lowestChild(node.getRight());
            delete(lowestChild(node.getRight()).getVal());
            node.setVal(temp.getVal());
        }

    }

    // returns the lowest child of the given root
    // node by travelling to the left most child
    private BNode lowestChild(BNode root){
        if(root==null || root.getLeft()==null){
            return null;
        }
        BNode node = root;
        while(node.getLeft()!=null){
            node = node.getLeft();
        }
        return node;
    }

    // given the root node of a tree and a data value of n,
    // tries to find the node with value n under the root
    private BNode findNode(BNode node, int n){
        if(node == null){
            return null;
        }
        while(node!=null && node.getVal()!=n){
            if(n<node.getVal()){
                node=node.getLeft();
            }
            else{
                node=node.getRight();
            }
        }
        return node;
    }

    @Override
    public String toString(){
        String ans = stringify(root);
        if(ans!=""){
            ans = ans.substring(0,ans.length()-2);
        }
        return "{"+ans+"}";
    }

    // returns a string of the binary tree's data in-order (l,n,r)
    public String stringify(BNode branch){
        if(branch != null){
            return stringify(branch.getLeft()) +
                    branch.getVal() + ", " +
                    stringify(branch.getRight());
        }
        return "";
    }
}