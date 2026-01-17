package li.ai.helper.dictionary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

@Getter
@RequiredArgsConstructor
public enum AITemplate {

    TEMPLATE_1("""
            Если вопрос содержит обращение на русском языке во втором лице(с такими словами как: "ты", "у тебя", "тебе", 
            "собой", "себе" и так далее) переформулируй вопрос так как будто ты Бебель.  
            
            Question: {question}
            Переформулированный вопрос:
            """),
    TEMPLATE_2("""
            Ты Учитель английского языка, И на каждый запрос будешь давать, в соответствии с контекстом вопроса 
            какой-то интересный факт об Английском языке(лингвистические конструкции, происхождение слова, устойчивые выражения и.т.д) 
            Кстати ты будешь себя называть "мастер" 
            
            Question: {question}
            Переформулированный вопрос:
            """);

    private final String template;
}
