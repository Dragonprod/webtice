/* eslint-disable no-unused-vars */
import {
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Paper,
} from "@mui/material";
import React, { PureComponent } from "react";
import styles from "./TagPage.module.css";
// import CodeEditor from "../CodeEditor";

class TagPage extends PureComponent {
  render() {
    const { name, description, attrs } = this.props;
    const isAttrs = attrs.length > 0 ? true : false;

    let desc = description.length > 0 ? description : "Описание отсутствует";

    return (
      <React.Fragment>
        <div className={styles.textContainer}>
          <h2 className={styles.themeTitle}>{name}</h2>
          <p className={styles.descriptionText}>{desc}</p>
          {/* <CodeEditor /> */}
          {isAttrs && (
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label="properties table">
                <TableHead>
                  <TableRow>
                    <TableCell align="left">Свойство</TableCell>
                    <TableCell align="left">Описание</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {attrs.map((attr) => (
                    <TableRow
                      sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell align="left">{attr.attributeName}</TableCell>
                      <TableCell align="left">{attr.description}</TableCell>
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

export default TagPage;
