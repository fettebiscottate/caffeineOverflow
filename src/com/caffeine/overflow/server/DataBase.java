package com.caffeine.overflow.server;

import java.io.File;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * 
 *
 * @author Giacomo Minello
 * @author Matteo Tramontano
 * @author Davide Menetto
 * @version 1.0
 */
public class DataBase {

	public static final String CATEGORIES = "Categories";

	public static final String QUESTIONS = "Questions";

	public static final String USERS = "Users";

	public static final String ANSWERS = "Answers";

	public static final String DATABASENAME = "db";

	private static DB getDB() {
		return DBMaker.newFileDB(new File(DATABASENAME)).make();
	}
	
	public static boolean createAnswer(Answer answer) {
		final DB dataBase = getDB();
		final BTreeMap<Integer, Answer> questions = dataBase.getTreeMap(ANSWERS);
		switch (answer.getText().length()) {
		case 0:
			return false;
		default:
			answer.setIdAnswer(questions.isEmpty() ? 0 : (questions.lastKey() + 1));
			questions.put(answer.getIdAnswer(), answer);
			dataBase.commit();
			dataBase.close();
			return true;
		}
	}
	
	public static List<Answer> readAnswer(int idQuestion) {

		final DB dataBase = getDB();
		final BTreeMap<Integer, Answer> questions = dataBase.getTreeMap(ANSWERS);

		final List<Answer> questionsList = new ArrayList<>();

		for (Iterator<Entry<Integer, Answer>> iterator = questions.entrySet().iterator(); iterator.hasNext();) {
			final Map.Entry<Integer, Answer> answer = iterator.next();
			if (answer.getValue().getIdQuestion() == idQuestion) {
				questionsList.add(answer.getValue());
			}
		}
		dataBase.commit();
		dataBase.close();
		Collections.reverse(questionsList);
		return questionsList;
	}

	public static List<Answer> readAnswers() {

		final DB dataBase = getDB();
		final BTreeMap<Integer, Answer> questions = dataBase.getTreeMap(ANSWERS);

		final List<Answer> questionsList = new ArrayList<>();

		for (Iterator<Entry<Integer, Answer>> iterator = questions.entrySet().iterator(); iterator.hasNext();) {
			final Map.Entry<Integer, Answer> answer = iterator.next();
			questionsList.add(answer.getValue());
		}
		dataBase.commit();
		dataBase.close();

		Collections.reverse(questionsList);

		return questionsList;
	}
	
	public static boolean deleteAnswer(int id) {

		final DB dataBase = getDB();
		final BTreeMap<Integer, Answer> questions = dataBase.getTreeMap(ANSWERS);
		questions.remove(id);
		dataBase.commit();
		dataBase.close();
		return true;
	}


    public static boolean createQuestion(Question question) {
		final DB dataBase = getDB();
		final BTreeMap<Integer, Question> questions = dataBase.getTreeMap(QUESTIONS);
		switch (question.getText().length()) {
		case 0:
			return false;
		default:
			final int id = setQuestionId();
			question.setIdQuestion(id);
			questions.put(question.getIdQuestion(), question);
			dataBase.commit();
			dataBase.close();
			return true;
		}

	}
	
	private static int setQuestionId() {
		final DB dataBase = getDB();
		final BTreeMap<Integer, Question> questions = dataBase.getTreeMap(QUESTIONS);
		return questions.isEmpty() ? 0 : (questions.lastKey() + 1);
	}
	
	public static List<Question> readQuestions() {
		final DB dataBase = getDB();
		final BTreeMap<Integer, Question> questions = dataBase.getTreeMap(QUESTIONS);
		final List<Question> domande = new ArrayList<>();
		if (!questions.isEmpty()) {
			for (Iterator<Entry<Integer, Question>> iterator = questions.entrySet().iterator(); iterator.hasNext();) {
				final Map.Entry<Integer, Question> d = iterator.next();
				domande.add(d.getValue());
			}
		}
		Collections.reverse(domande);
		return domande;
	}
	
	public static boolean removeQuestion(int id) {

		if (!isValid(id)) {
			return false;
		} else {
			final DB dataBase = getDB();
			final BTreeMap<Integer, Question> questions = dataBase.getTreeMap(QUESTIONS);
			final BTreeMap<Integer, Answer> answers = dataBase.getTreeMap(ANSWERS);

			for (Iterator<Entry<Integer, Answer>> iterator = answers.entrySet().iterator(); iterator.hasNext();) {
				final Entry<Integer, Answer> entry = iterator.next();
				if (entry.getValue().getIdQuestion() == id) {
					answers.remove(entry.getKey());
				}
			}

			questions.remove(id);
			dataBase.commit();
			dataBase.close();
			return true;
		}
	}
	
	private DataBase() {
		throw new IllegalStateException("Utility class");
	}

}