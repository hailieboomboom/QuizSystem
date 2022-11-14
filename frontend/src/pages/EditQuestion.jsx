import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Button from '@mui/material/Button';
import { useState } from 'react';
import { Container } from '@mui/material';
import { Link } from "react-router-dom";
import {useRecoilState} from "recoil";
import {editQuestionState} from "../recoil/Atoms";

const EditQuestion = () => {
    const [editQuestions, setEditQuestions] = useRecoilState(editQuestionState)

    const [question, setQuestion] = useState('');

    function handleSaveQuestion() {

    }

    return (
        <React.Fragment>
            <Container>
                <Typography variant="h6" gutterBottom>
                    Edit Question: {editQuestions.questionDetail}
                </Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <FormControl variant="standard"  fullWidth>
                            <TextField
                                required
                                id="question"
                                name="questionString"
                                label="Question"
                                fullWidth
                                variant="standard"
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        <FormControl variant="standard"  fullWidth>
                            {editQuestions.mcqOptionDtoList.map((option) => (
                                <TextField
                                    required
                                    id="Answer"
                                    name="questionString"
                                    fullWidth
                                    variant="standard"
                                    defaultValue={option.optionDescription}
                                > {option.optionDescription} </TextField>
                            ))}

                        </FormControl>
                    </Grid>

                </Grid>

                    <Grid item xs={1}>

                        <Button  onClick={handleSaveQuestion} variant="outlined" as={Link} to="/successEditQuestion">
                            Edit Question
                        </Button>
                    </Grid>
            </Container>

        </React.Fragment>
    )
}

export default EditQuestion
