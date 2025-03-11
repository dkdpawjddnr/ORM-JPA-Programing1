package hellojpa;

public class ValueMain {
    public static void main(String[] args) {

        int a = 10;
        int b = 10;

        System.out.println("a == b: " + (a == b));

        Address address1 = new Address("city","street", "10");
        Address address2 = new Address("city","street", "10");

        // 동일성 비교 인스턴스의 참조 값을 비교 == 사용
        System.out.println("address1 == address2: " + (address1 == address2));

        // 동등성 비교 인스턴스의 값을 비교, 오버라이딩해서 equals()사용
        System.out.println("address1 equals address2: " + (address1.equals(address2)));
    }
}
