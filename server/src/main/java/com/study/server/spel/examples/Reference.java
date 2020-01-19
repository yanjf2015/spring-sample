package com.study.server.spel.examples;

import com.study.server.spel.inventor.Inventor;
import com.study.server.spel.inventor.PlaceOfBirth;
import com.study.server.spel.inventor.Society;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class Reference {

    public static void main(String[] args) {
        SpelParserConfiguration configuration = new SpelParserConfiguration(SpelCompilerMode.OFF, null,
                true, true, 5);
        ExpressionParser parser = new SpelExpressionParser(configuration);
        // Create and set a calendar
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

        Society society = new Society();
        EvaluationContext societyContext = SimpleEvaluationContext.forReadWriteDataBinding().withRootObject(society).build();
        System.out.println(society.list);
        Expression expression = parser.parseExpression("list[3]");
        expression.getValue(society);
        System.out.println(society.list);

        System.out.println(society.getMembers());
        expression = parser.parseExpression("members[3]");
//        expression.getValue(society);
        System.out.println(society.getMembers());

        // The constructor arguments are name, birthday, and nationality.
        PlaceOfBirth placeOfBirth = new PlaceOfBirth("beijing", "china");
        tesla.setPlaceOfBirth(placeOfBirth);
        String[] inventions = {"first", "two", "three", "four"};
        tesla.setInventions(inventions);
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().withRootObject(tesla).build();
        int year = (Integer) parser.parseExpression("Birthdate.Year + 1900").getValue(context);
        System.out.println(year);
        String city = (String) parser.parseExpression("placeOfBirth.City").getValue(tesla);
        System.out.println(city);

        // evaluates to "Induction motor"
        String invention = parser.parseExpression("inventions[3]").getValue(
                context, tesla, String.class);
        System.out.println(invention);
        // Members List
        Society ieee = new Society();
        ieee.getMembers().add(tesla);
        ieee.getOfficers().put(Society.President, tesla);
        // evaluates to "Nikola Tesla"
        String name = parser.parseExpression("Members[0].Name").getValue(
                context, ieee, String.class);
        System.out.println(name);
        // List and Array navigation
        // evaluates to "Wireless communication"
        invention = parser.parseExpression("Members[0].Inventions[1]").getValue(
                context, ieee, String.class);
        System.out.println(invention);

        Inventor pupin = parser.parseExpression("Officers['president']").getValue(
                context, ieee, Inventor.class);
        System.out.println(pupin.getName());
        // evaluates to "Idvor"
        city = parser.parseExpression("Officers['president'].PlaceOfBirth.City").getValue(
                context, ieee, String.class);
        System.out.println(city);
        // setting values
        parser.parseExpression("Officers['president'].PlaceOfBirth.Country").setValue(
                ieee, "Croatia");
        String country = parser.parseExpression("Officers['president'].PlaceOfBirth.Country").getValue(
                context, ieee, String.class);
        System.out.println(country);

        // evaluates to a Java list containing the four numbers
        List numbers = (List) parser.parseExpression("{1,2,3,4}").getValue(context);
        System.out.println(numbers);
        List listOfLists = (List) parser.parseExpression("{{'a','b'},{'x','y'}}").getValue(context);
        System.out.println(listOfLists);

        // evaluates to a Java map containing the two entries
        Map inventorInfo = (Map) parser.parseExpression("{name:'Nikola',dob:'10-July-1856'}").getValue(context);
        System.out.println(inventorInfo);
        Map mapOfMaps = (Map) parser.parseExpression("{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}").getValue(context);
        System.out.println(mapOfMaps);

        Inventor inventor = new Inventor();
        parser.parseExpression("name").setValue(inventor, "Aleksandar Seovic");
        System.out.println(parser.parseExpression("Name").getValue(inventor));

        // alternatively
        System.out.println(parser.parseExpression(
                "Name = 'Aleksandar'").getValue(inventor, String.class));

        Inventor a = new Inventor("Nikola Tesla", "Serbian");

        context.setVariable("newName", "Mike Tesla");
        parser.parseExpression("Name = #newName").getValue(context, a);
        System.out.println(a.getName());

        String randomPhrase = parser.parseExpression(
                "random number is #{T(java.lang.Math).random()}",
                new TemplateParserContext()).getValue(String.class);
        System.out.println(randomPhrase);
    }

}
