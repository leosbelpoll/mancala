import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";

import User from "./User";

let container = null;
beforeEach(() => {
  container = document.createElement("div");
  document.body.appendChild(container);
});

afterEach(() => {
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it("render simple user", () => {
  act(() => {
    render(<User email={"test@test.com"} />, container);
  });
  const h2 = container.querySelector("h2");
  expect(container.textContent).toBe("test@test.com");
  expect(h2.getAttribute("class")).toBe("user");
});

it("render bottom user", () => {
  act(() => {
    render(<User email={"test@test.com"} bottomUser />, container);
  });
  const h2 = container.querySelector("h2");
  expect(container.textContent).toBe("test@test.com");
  expect(h2.getAttribute("class")).toBe("user bottom-user");
});

it("render active user", () => {
  act(() => {
    render(<User email={"test@test.com"} active />, container);
  });
  const h2 = container.querySelector("h2");
  expect(container.textContent).toBe("test@test.com");
  expect(h2.getAttribute("class")).toBe("user active");
});

it("render winner user", () => {
  act(() => {
    render(<User email={"test@test.com"} winner />, container);
  });
  const h2 = container.querySelector("h2");
  expect(container.textContent).toBe("test@test.com");
  expect(h2.getAttribute("class")).toBe("user winner");
});
