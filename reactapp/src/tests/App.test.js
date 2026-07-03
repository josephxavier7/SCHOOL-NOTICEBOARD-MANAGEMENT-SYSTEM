// src/__tests__/Notice.test.js
import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter, Routes, Route, BrowserRouter } from 'react-router-dom';
import AddNotice from '../components/AddNotice';
import ViewNotice from '../components/ViewNotice';
import EditNotice from '../components/EditNotice';
import NoticeService from '../services/NoticeService';
import "@testing-library/jest-dom"
jest.mock('../services/NoticeService');

beforeEach(() => {
  jest.clearAllMocks();
});

describe('Notice Management Tests', () => {
  // ---------------- AddNotice ----------------
  test('React_BuildUIComponents_renders AddNotice form fields', () => {
    render(
      <BrowserRouter>
        <AddNotice />
      </BrowserRouter>
    );

    const titleInput = document.querySelector('input[name="noticeTitle"]');
    const contentTextarea = document.querySelector('textarea[name="noticeContent"]');
    const dateInput = document.querySelector('input[name="noticeDate"]');
    const categoryInput = document.querySelector('input[name="noticeCategory"]');
    const submitButton = screen.getByRole('button', { name: /Add Notice/i });

    expect(titleInput).toBeInTheDocument();
    expect(contentTextarea).toBeInTheDocument();
    expect(dateInput).toBeInTheDocument();
    expect(categoryInput).toBeInTheDocument();
    expect(submitButton).toBeInTheDocument();
  });

  test('React_APIIntegration_TestingAndAPIDocumentation_submits AddNotice form successfully', async () => {
    NoticeService.addNotice.mockResolvedValue({ data: { id: 1 } });

    render(
      <BrowserRouter>
        <AddNotice />
      </BrowserRouter>
    );

    const titleInput = document.querySelector('input[name="noticeTitle"]');
    const contentTextarea = document.querySelector('textarea[name="noticeContent"]');
    const dateInput = document.querySelector('input[name="noticeDate"]');
    const categoryInput = document.querySelector('input[name="noticeCategory"]');
    const submitButton = screen.getByRole('button', { name: /Add Notice/i });

    fireEvent.change(titleInput, { target: { value: 'Test Title' } });
    fireEvent.change(contentTextarea, { target: { value: 'Some content' } });
    fireEvent.change(categoryInput, { target: { value: 'General' } });
    fireEvent.change(dateInput, { target: { value: '2025-08-08' } });

    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByText(/Notice added successfully!/i)).toBeInTheDocument();
      expect(NoticeService.addNotice).toHaveBeenCalledWith({
        noticeTitle: 'Test Title',
        noticeContent: 'Some content',
        noticeDate: '2025-08-08',
        noticeCategory: 'General'
      });
    });
  });

  test('React_UITestingAndResponsivenessFixes_shows error message when AddNotice fails', async () => {
    NoticeService.addNotice.mockRejectedValue(new Error('Failed to add'));

    render(
      <BrowserRouter>
        <AddNotice />
      </BrowserRouter>
    );

    const titleInput = document.querySelector('input[name="noticeTitle"]');
    const contentTextarea = document.querySelector('textarea[name="noticeContent"]');
    const dateInput = document.querySelector('input[name="noticeDate"]');
    const categoryInput = document.querySelector('input[name="noticeCategory"]');
    const submitButton = screen.getByRole('button', { name: /Add Notice/i });

    fireEvent.change(titleInput, { target: { value: 'Bad Title' } });
    fireEvent.change(contentTextarea, { target: { value: 'Bad content' } });
    fireEvent.change(categoryInput, { target: { value: 'Error' } });
    fireEvent.change(dateInput, { target: { value: '2025-08-08' } });

    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByText(/Failed to add notice./i)).toBeInTheDocument();
    });
  });

  // ---------------- ViewNotice ----------------
  test('React_UITestingAndResponsivenessFixes_renders No notices available when empty', async () => {
    NoticeService.getAllNotices.mockResolvedValue({ data: [] });

    render(
      <BrowserRouter>
        <ViewNotice />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText(/No notices available/i)).toBeInTheDocument();
    });
  });

  test('React_APIIntegration_TestingAndAPIDocumentation_renders notices in table when data is present', async () => {
    NoticeService.getAllNotices.mockResolvedValue({
      data: [
        {
          noticeId: 1,
          noticeTitle: 'Notice 1',
          noticeContent: 'Content 1',
          noticeDate: '2025-08-08',
          noticeCategory: 'General'
        }
      ]
    });

    render(
      <BrowserRouter>
        <ViewNotice />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText(/Notice 1/i)).toBeInTheDocument();
      expect(screen.getByText(/Content 1/i)).toBeInTheDocument();
      expect(screen.getByText(/2025-08-08/i)).toBeInTheDocument();
      expect(screen.getByText(/General/i)).toBeInTheDocument();
    });
  });

  test('React_APIIntegration_TestingAndAPIDocumentation_calls deleteNotice when delete button is clicked', async () => {
    NoticeService.getAllNotices.mockResolvedValue({
      data: [
        {
          noticeId: 2,
          noticeTitle: 'Notice 2',
          noticeContent: 'Content 2',
          noticeDate: '2025-08-08',
          noticeCategory: 'Info'
        }
      ]
    });
    NoticeService.deleteNotice.mockResolvedValue({});

    render(
      <BrowserRouter>
        <ViewNotice />
      </BrowserRouter>
    );

    await waitFor(() => screen.getByText(/Notice 2/i));
    // find delete button in the row (first Delete button on screen)
    const deleteButton = screen.getByRole('button', { name: /Delete/i });
    fireEvent.click(deleteButton);

    await waitFor(() => {
      expect(NoticeService.deleteNotice).toHaveBeenCalledWith(2);
    });
  });

  // ---------------- EditNotice ----------------
  test('React_APIIntegration_TestingAndAPIDocumentation_loads existing notice into EditNotice form', async () => {
    NoticeService.getNoticeById.mockResolvedValue({
      data: {
        noticeTitle: 'Old Title',
        noticeContent: 'Old Content',
        noticeDate: '2025-08-08',
        noticeCategory: 'General'
      }
    });

    render(
      <MemoryRouter initialEntries={['/edit/5']}>
        <Routes>
          <Route path="/edit/:id" element={<EditNotice />} />
        </Routes>
      </MemoryRouter>
    );

    expect(await screen.findByDisplayValue('Old Title')).toBeInTheDocument();
    expect(screen.getByDisplayValue('Old Content')).toBeInTheDocument();
  });

  test('React_APIIntegration_TestingAndAPIDocumentation_submits updated notice in EditNotice', async () => {
    NoticeService.getNoticeById.mockResolvedValue({
      data: {
        noticeTitle: 'Title',
        noticeContent: 'Content',
        noticeDate: '2025-08-08',
        noticeCategory: 'Category'
      }
    });
    NoticeService.updateNotice.mockResolvedValue({});

    render(
      <MemoryRouter initialEntries={['/edit/10']}>
        <Routes>
          <Route path="/edit/:id" element={<EditNotice />} />
        </Routes>
      </MemoryRouter>
    );

    const titleInput = await screen.findByDisplayValue('Title');
    fireEvent.change(titleInput, { target: { value: 'Updated Title' } });

    fireEvent.click(screen.getByText(/Update Notice/i));

    await waitFor(() => {
      expect(NoticeService.updateNotice).toHaveBeenCalledWith('10', {
        noticeTitle: 'Updated Title',
        noticeContent: 'Content',
        noticeDate: '2025-08-08',
        noticeCategory: 'Category'
      });
    });
  });
});
