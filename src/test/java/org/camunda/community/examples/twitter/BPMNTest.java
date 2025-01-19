package org.camunda.community.examples.twitter;

import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceCompleted;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.spring.test.ZeebeSpringTest;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ZeebeSpringTest
@SpringBootTest
public class BPMNTest {

  @Autowired ZeebeClient zeebeClient;

  // @Autowired UserTaskService userTaskService;

  @Test
  void TestBPMN() {
    ProcessInstanceEvent process =
        zeebeClient
            .newCreateInstanceCommand()
            .bpmnProcessId("Process_16dlx30")
            .latestVersion()
            .send()
            .join();
    // userTaskService.complete(awaitUserTask().getKey(), JsonNodeFactory.instance.objectNode());
    waitForProcessInstanceCompleted(process, Duration.ofSeconds(30));
    BpmnAssert.assertThat(process)
        .hasPassedElement("StartEvent_1", 1)
        .hasPassedElement("Event_0g4pe94", 1);
  }
}
