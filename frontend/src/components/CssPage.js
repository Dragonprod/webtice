/* eslint-disable no-unused-vars */
import {
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Paper,
} from '@mui/material';
import React, { PureComponent } from 'react';
import styles from '../styles//ReferenceBookPage.module.css';

class CssPage extends PureComponent {
  render() {
    const { name, description, syntax, values } = this.props;
    const isValues = values.length > 0 ? true : false;

    let desc = description.length > 0 ? description : 'Описание отсутствует';

    return (
      <React.Fragment>
        <div className={styles.textContainer}>
          <h2 className={styles.themeTitle}>{name}</h2>
          <p className={styles.descriptionText}>{desc}</p>
          <code className={`${syntax.length > 0 ? styles.codeCard : ''}`}>
            {syntax}
          </code>
          {isValues && (
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label='properties table'>
                <TableHead>
                  <TableRow>
                    <TableCell align='left'>Свойство</TableCell>
                    <TableCell align='left'>Описание</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {values.map(value => (
                    <TableRow
                      sx={{
                        '&:last-child td, &:last-child th': { border: 0 },
                      }}>
                      <TableCell align='left'>{value.valueName}</TableCell>
                      <TableCell align='left'>{value.description}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          )}
        </div>
      </React.Fragment>
    );
  }
}

export default CssPage;
