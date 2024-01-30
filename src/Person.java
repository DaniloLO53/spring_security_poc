public class Person {
  private String[] languages = {"javascript", "C", "java"};
  private static int age = 27;

  public void showAge() {
    System.out.println(age);
  }

  public void showLanguages() {
    for (int i = 0; i < this.languages.length; i++) {
      System.out.println(this.languages[i]);
    }
  }

  public int sumValues(int... values) {
    int sum = 0;

    for (int i = 0; i < values.length; i++) {
      sum += values[i];
    }

    return sum;
  }
}
