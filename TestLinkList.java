/**
* 链表：定义一个链表类，链表每个节点包含指针域和数据域，假设数据域中存放的均为整数。该链表具备以下功能：
* 创建空链表；
* 根据一个给定的整数数组创建链表；
* 打印链表的数据信息；
* 删除数据域中值为w的节点（如果有多个则删除多个）；
* 在第n个节点后插入节点；
*/
public class TestLinkList{
	static int test [] = new int[20];
	public static void main(String [] args){
		System.out.println("测试建空表并插入两个节点。");
		LinkList testEmptyList = new LinkList();
		System.out.println("建空表。");
		testEmptyList.prinrt();
		System.out.println("插入两个节点。");
		testEmptyList.insert(0,10);
		testEmptyList.insert(1,20);
		testEmptyList.prinrt();
		System.out.println("测试按数组建链表并删除一个节点。");
		generateTestArray();
		LinkList testArrary = new LinkList(test);
		testArrary.prinrt();
		System.out.println("删除一个节点。");
		testArrary.deleteNode(1 + (int)(20 * Math.random()));
		testArrary.prinrt();
		
	}
	private static void generateTestArray(){
		for (int j = 0; j < test.length; j++) {
			test[j] = 1 + (int)(20 * Math.random());
		}
	}
}

class LinkList{
	// 带头节点
	private Node head;
	public LinkList(){
		head = new Node(0);
	}
	public LinkList(int [] dataList){
		head = new Node(0);
		Node current = head;
		for(int data : dataList){
			Node newNode = new Node(data);
			current.next = newNode;
			current = current.next;
		}
	}
	public void deleteNode(int w){
		Node current = head.next;
		Node previous = head;
		int i = 0;
		while(current != null){
			if(current.data == w){
				previous.next = current.next;
				current = current.next;
				i++;
				continue;
			}
			previous = current;
			current = current.next;
		}
		if(i == 0) System.out.println("没有含 " + w + " 的节点。");
		else System.out.println("已删除 " + i + " 个为 " + w + "的节点。");
	}
	public void prinrt(){
		Node current = head.next;
		if(current == null){
			System.out.print("空表。");
		}
		while(current != null){
			current.display();
			current = current.next;
		}
		System.out.println();
	}
	public void insert(int n , int data){
		int i = 0;
		Node current = head;
		while(current != null && i != n){
			i++;
			current = current.next;
		}
		if(current == null){
			System.out.println("非法的插入索引。");
			return;
		} 
		Node x = new Node(data);
		x.next = current.next;
		current.next = x;
	}
}
class Node{
	protected int data;
	protected Node next = null;
	public Node(int data){
		this.data = data;
	}
	public void display(){
		System.out.print(data + " ");
	}
}