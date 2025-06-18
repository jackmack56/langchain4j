package dev.langchain4j.model.openai;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.ModelProvider;
import dev.langchain4j.model.chat.Capability;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.internal.chat.ChatCompletionResponse;
import dev.langchain4j.model.chat.AiChatModelListenerUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static dev.langchain4j.model.ModelProvider.OTHER;

public interface AiStreamChatModel {
    /**
     * This is the main API to interact with the chat model.
     *
     * @param chatRequest a {@link ChatRequest}, containing all the inputs to the LLM
     * @param handler     a {@link StreamingChatResponseHandler} that will handle streaming response from the LLM
     */
    default void chat(ChatRequest chatRequest, AiStreamingChatResponseHandler handler) {

        ChatRequest finalChatRequest = ChatRequest.builder()
                .messages(chatRequest.messages())
                .parameters(defaultRequestParameters().overrideWith(chatRequest.parameters()))
                .build();

        List<ChatModelListener> listeners = listeners();
        Map<Object, Object> attributes = new ConcurrentHashMap<>();

        AiStreamingChatResponseHandler observingHandler = new AiStreamingChatResponseHandler() {

            @Override
            public void onPartialResponse(ChatCompletionResponse partialResponse) {

                handler.onPartialResponse(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                AiChatModelListenerUtils.onResponse(completeResponse, finalChatRequest, provider(), attributes, listeners);
                handler.onCompleteResponse(completeResponse);
            }

            @Override
            public void onError(Throwable error) {
                AiChatModelListenerUtils.onError(error, finalChatRequest, provider(), attributes, listeners);
                handler.onError(error);
            }
        };

        AiChatModelListenerUtils.onRequest(finalChatRequest, provider(), attributes, listeners);
        doChat(finalChatRequest, observingHandler);
    }

    default void doChat(ChatRequest chatRequest, AiStreamingChatResponseHandler handler) {
        throw new RuntimeException("Not implemented");
    }

    default ChatRequestParameters defaultRequestParameters() {
        return ChatRequestParameters.builder().build();
    }

    default List<ChatModelListener> listeners() {
        return List.of();
    }

    default ModelProvider provider() {
        return OTHER;
    }

    default void chat(String userMessage, AiStreamingChatResponseHandler handler) {

        ChatRequest chatRequest = ChatRequest.builder()
                .messages(UserMessage.from(userMessage))
                .build();

        chat(chatRequest, handler);
    }

    default void chat(List<ChatMessage> messages, AiStreamingChatResponseHandler handler) {
        ChatRequest chatRequest = ChatRequest.builder()
                .messages(messages)
                .build();
        chat(chatRequest, handler);
    }

    default Set<Capability> supportedCapabilities() {
        return Set.of();
    }
}
