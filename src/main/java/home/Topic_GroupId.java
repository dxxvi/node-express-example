package home;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public record Topic_GroupId(
    String topic,
    String groupId,
    BlockingQueue<Partition_Key_Value> queue,
    MessageGettingRunnable messageGettingRunnable) {
  public Topic_GroupId {
    Objects.requireNonNull(topic);
    Objects.requireNonNull(groupId);
    Objects.requireNonNull(queue);
    Objects.requireNonNull(messageGettingRunnable);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Topic_GroupId that = (Topic_GroupId) obj;
    return topic.equals(that.topic) && groupId.equals(that.groupId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(topic, groupId);
  }
}
