package dev.langchain4j.model.openai.spi;

import dev.langchain4j.Internal;
import dev.langchain4j.model.openai.AiStreamingChatModel;
import java.util.function.Supplier;

@Internal
public interface AiStreamingChatModelBuilderFactory extends Supplier<AiStreamingChatModel.AiStreamingChatModelBuilder>{
}
