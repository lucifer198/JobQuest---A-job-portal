import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  isAuthenticated: false,
  isRecruiter: false,
  userData: null,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    login: (state, action) => {
      state.isAuthenticated = true;
      state.isRecruiter = action.payload.isRecruiter;

      // ✅ Ensure jobIds is always an array
      const userData = action.payload.userData;
      userData.jobIds = userData.jobIds || [];

      state.userData = userData;
    },
    logout: (state) => {
      state.isAuthenticated = false;
      state.isRecruiter = false;
      state.userData = null;
    },
    addJobIdToRecruiter: (state, action) => {
      if (state.isRecruiter) {
        state.userData.jobIds = state.userData.jobIds || [];
        state.userData.jobIds.push(action.payload.jobId);
      }
    },
  },
});

export const { login, logout, addJobIdToRecruiter } = authSlice.actions;

export default authSlice.reducer;
