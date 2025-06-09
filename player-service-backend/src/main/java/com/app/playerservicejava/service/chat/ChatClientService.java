package com.app.playerservicejava.service.chat;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.Model;
import io.github.ollama4j.models.OllamaResult;
import io.github.ollama4j.types.OllamaModelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.PromptBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@Service
public class ChatClientService {

    @Autowired
    private OllamaAPI ollamaAPI;

    public List<Model> listModels() throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        List<Model> models = ollamaAPI.listModels();
        return models;
    }

    public String chatV2(String prompt, HashMap<String, String> contextVars) {
        String model = OllamaModelType.TINYLLAMA;

        for (String key : contextVars.keySet()) {
            String placeholder = "{{" + key + "}}";
            String value = contextVars.get(key);
            if (value != null) {
                prompt = prompt.replace(placeholder, value);
            }
        }

        PromptBuilder promptBuilder =
                new PromptBuilder()
                        .addLine(prompt);

        boolean raw = false;
        OllamaResult response = null;
        try {
            response = ollamaAPI.generate(model, promptBuilder.build(), raw, new OptionsBuilder().build());
        } catch (OllamaBaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response.getResponse();
    }

    public String chat() throws OllamaBaseException, IOException, InterruptedException {
        String model = OllamaModelType.TINYLLAMA;

        PromptBuilder promptBuilder =
                new PromptBuilder()
                        .addLine("Recite a haiku about recursion.");

        /** Sample prompt
         *
        PromptBuilder promptBuilder =
                new PromptBuilder()
                        .addLine("You are an expert coder and understand different programming languages.")
                        .addLine("Given a question, answer ONLY with code.")
                        .addLine("Produce clean, formatted and indented code in markdown format.")
                        .addLine(
                                "DO NOT include ANY extra text apart from code. Follow this instruction very strictly!")
                        .addLine("If there's any additional information you want to add, use comments within code.")
                        .addLine("Answer only in the programming language that has been asked for.")
                        .addSeparator()
                        .addLine("Example: Sum 2 numbers in Python")
                        .addLine("Answer:")
                        .addLine("```python")
                        .addLine("def sum(num1: int, num2: int) -> int:")
                        .addLine("    return num1 + num2")
                        .addLine("```")
                        .addSeparator()
                        .add("How do I read a file in Go and print its contents to stdout?");
         **/

        boolean raw = false;
        OllamaResult response = ollamaAPI.generate(model, promptBuilder.build(), raw, new OptionsBuilder().build());
        return response.getResponse();
    }

}
