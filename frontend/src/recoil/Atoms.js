import  { atom } from 'recoil';
import QuestionData from "../data/QuestionData"

export const textState = atom({
    key: 'textState', // unique ID (with respect to other atoms/selectors)
    default: '', // default value (aka initial value)
});

export const attemptQuizState = atom({
    key: 'attemptQuizState', // unique ID (with respect to other atoms/selectors)
    default: [], // default value (aka initial value)
});
export const createQuizSelectedQuestions = atom({
    key: 'createQuizSelectedQuestionsState', // unique ID (with respect to other atoms/selectors)
    default: [], // default value (aka initial value)
});
export const createQuizAllQuestions = atom({
    key: 'createQuizAllQuestionsState', // unique ID (with respect to other atoms/selectors)
    default: [], // default value (aka initial value)
});
export const createAllQuestions = atom({
    key: 'createAllQuestionsState', // unique ID (with respect to other atoms/selectors)
    default: [], // default value (aka initial value)
});
export const editQuestionState = atom({
    key: 'editQuestionState',
    default: [],
})
export const quizSelectedAnswersState = atom({
    key: 'quizSelectedAnswersState',
    default: [],
})
