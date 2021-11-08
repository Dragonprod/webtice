/* eslint-disable no-unused-vars */
import { render, screen } from "@testing-library/react";
import Menu from "./components/Menu";
import LandingPage from "./pages/LangingPage";

test("renders main title part one", () => {
  render(<LandingPage />);

  const titlePartOne = screen.getByText(/Начни учиться прямо/i);
  expect(titlePartOne).toBeInTheDocument();
});

test("renders main title part two", () => {
  render(<LandingPage />);

  const titlePartTwo = screen.getByText(/сейчас/i);
  expect(titlePartTwo).toBeInTheDocument();
});

test("renders description", () => {
  render(<LandingPage />);
  const linkElement = screen.getByText(
    /Webtice — образовательная платформа РТУ МИРЭА для обучения студентов веб-разработке и проведения экзаменов, преимущественно по компьютерным дисциплинам/i
  );
  expect(linkElement).toBeInTheDocument();
});

test("renders menu", () => {
  render(<Menu />);

  const logoText = screen.getByText(/Webtice/i);
  expect(logoText).toBeInTheDocument();

  const mainText = screen.getByText(/Главная/i);
  expect(mainText).toBeInTheDocument();

  const refBookText = screen.getByText(/Веб-справочник/i);
  expect(refBookText).toBeInTheDocument();

  const codeText = screen.getByText(/Онлайн кодинг/i);
  expect(codeText).toBeInTheDocument();

  const examText = screen.getByText(/Экзамен/i);
  expect(examText).toBeInTheDocument();

  const aboutText = screen.getByText(/О проекте/i);
  expect(aboutText).toBeInTheDocument();
});

// test('renders tags menu', () => {
//   render(<ReferenceBookMenu/>);

//   const logoText = screen.getByText(/Webtice/i);
//   expect(logoText).toBeInTheDocument();

//   const mainText = screen.getByText(/Главная/i);
//   expect(mainText).toBeInTheDocument();

//   const gitText = screen.getByText(/GitHub/i);
//   expect(gitText).toBeInTheDocument();

// });
