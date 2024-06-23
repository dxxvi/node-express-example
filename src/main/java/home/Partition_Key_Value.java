package home;

public record Partition_Key_Value(int partition, String key, String value) {
  public Partition_Key_Value {
    if (partition < 0) throw new IllegalArgumentException("partition " + partition + " is < 0");
  }
}
