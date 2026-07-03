import axios from "axios";

const API_URL = "http://localhost:8080/api/notices";

const getAllNotices = () => {
    return axios.get(`${API_URL}/all`);
};

const addNotice = (notice) => {
    return axios.post(`${API_URL}/add`, notice);
};

const getNoticeById = (id) => {
    return axios.get(`${API_URL}/${id}`);
};

const updateNotice = (id, notice) => {
    return axios.put(`${API_URL}/${id}`, notice);
};

const deleteNotice = (id) => {
    return axios.delete(`${API_URL}/${id}`);
};

const NoticeService = {
    getAllNotices,
    addNotice,
    getNoticeById,
    updateNotice,
    deleteNotice
};

export default NoticeService;
