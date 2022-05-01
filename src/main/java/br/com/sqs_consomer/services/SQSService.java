package br.com.sqs_consomer.services;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import com.google.gson.Gson;
import br.com.sqs_consomer.dto.infoPedido;

public class SQSService {
    public static void sendMessage() {
        AwsCredentialsProvider credentialsProvider = new AwsCredentialsProvider() {
            @Override
            public AwsCredentials resolveCredentials() {
                return new AwsCredentials() {
                    @Override
                    public String accessKeyId() {
                        return System.getenv("AWS_ACCESS_KEY");
                    }

                    @Override
                    public String secretAccessKey() {
                        return System.getenv("AWS_SECRET_KEY");
                    }
                };
            }
        };

        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build();

        infoPedido pedido = new infoPedido("pedido", "notaFiscal", 12);

        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl("queue_poc_ecommerce")
                .messageBody(new Gson().toJson(pedido))
                .build();
        sqsClient.sendMessage(sendMsgRequest);
    }
}
