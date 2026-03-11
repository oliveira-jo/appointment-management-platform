export interface AppointmentResponse {
  id: string
  customerName: string
  professionalName: string
  productName: string
  scheduledAt: string
  status: string
}

export interface AppointmentRequest {
  customerEmail: string
  professionalEmail: string
  productName: string
  scheduledAt: string
}

export interface MetricsResponse {
  today: number
  week: number
  revenueToday: number
  total: number
}

export interface Page<T> {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
  last: boolean
}