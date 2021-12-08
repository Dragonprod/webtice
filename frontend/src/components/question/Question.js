import React from 'react';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import Button from '@mui/material/Button';

import styles from './Question.module.css';

export default function Question(props) {
  const onClick = props.onClick;
  const theme = props.theme;
  const questionName = props.questionName;
  const answers = props.answers;

  return (
    <div className={styles.questionCard}>
      <h2 className={styles.testThemeTitle}>{theme}</h2>
      <h3 className={styles.questionName}>{questionName}</h3>
      <FormControl component='fieldset'>
        <RadioGroup
          aria-label='gender'
          defaultValue='female'
          name='radio-buttons-group'>
          {answers.map(ans => (
            <FormControlLabel
              className={styles.muiRadioButton}
              value={ans.answerName}
              control={
                <Radio
                  sx={{
                    color: '#fb39f9',
                    '&.Mui-checked': {
                      color: '#fb39f9',
                    },
                  }}
                />
              }
              label={ans.answerName}
            />
          ))}
        </RadioGroup>
        <Button
          className={styles.muiContinueButton}
          variant='contained'
          onClick={onClick}>
          Далее
        </Button>
      </FormControl>
    </div>
  );
}
