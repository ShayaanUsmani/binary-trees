public class BTree{
    private BNode root;

    public BTree(){
        root = null;
    }

    // constant variable used to refer to the order of the
    // tree display
    public static final int IN = 0;     //(left)(node)(right)
    public static final int PRE = 1;    //(node)(left)(right)
    public static final int POST = 2;   //(left)(right)(node)

    public BNode getRoot(){return root;}

    // adds a node into the tree with value n
    public void add(int n){
        BNode tmp = new BNode(n);
        if(root==null){
            root = tmp;
        }
        else{
            add(root, tmp);
        }
    }

    // add a node to a binary tree by comparing the adding node's (node)
    // value to the current node's (curr) value, if the value of curr is less
    // than node's, we call the same method again but with curr being the previous
    // curr's right node, if the value of curr is higher, we set the new curr to
    // curr's left. If we were suppose to go left (or right) and the left (or right)
    // node is null, we simply set curr's left (or right) node to the node we are
    // adding
    public void add(BNode curr, BNode node){
        if(node.getVal() > curr.getVal()){
            if(curr.getRight()==null){
                curr.setRight(node);
            }
            else{
                add(curr.getRight(),node);
            }
        }
        else if(node.getVal() < curr.getVal()){
            if(curr.getLeft()==null){
                curr.setLeft(node);
            }
            else{
                add(curr.getLeft(), node);
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

    // given integers 0,1, or 2, prints the binary tree's data in, pre, or post-order
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

    // delete a node given its value
    // we first find the node and then consider 4 cases
    // 1 - the node is the root -> set the root node to null
    // 2 - the node is a leaf -> set the node to null
    // 3 - the node only has a right or left child -> make
    // the node it's right or left child and set the right or
    // left child to null
    // 4 - the node is is the root of a subtree -> set the value of
    // the node to the lowest child to it's right since the lowest child
    // in the right will be less than all the other right children and
    // still be greater than the left child satisfying all rules of the
    // binary tree.
    public void delete(int n){
        if(root.getVal()==n){
            root = null;
            return;
        }
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

    // add method for adding a binary tree to another binary tree
    public void add(BTree t){
        addSubtree(t.root);
    }

    // adds all nodes of tree to another tree in Post order
    private void addSubtree(BNode root){
        if(root==null){
            return;
        }
        else{
            addSubtree(root.getLeft());
            addSubtree(root.getRight());
        }
        add(root.getVal());
    }

    // checks if the binary tree is height balanced
    public boolean isBalanced(){
        return checkHeightBalance(root);
    }

    // checks if the binary tree is height balanced by
    // comparing the height of it's children recursively.
    // if the absolute value of the difference of left subtree
    // and the right subtree is equal to or less than 1, the left
    // and right subtrees are both balanced, the tree is
    // balanced, otherwise it is not.
    private boolean checkHeightBalance(BNode node){
        if(node == null){
            return true;
        }
        if(Math.abs(height(node.getLeft(),0)-height(node.getRight(),0))<=1){
            return checkHeightBalance(node.getRight()) && checkHeightBalance(node.getLeft());
        }
        else{
            return false;
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