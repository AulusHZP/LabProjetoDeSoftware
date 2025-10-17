export type Json =
  | string
  | number
  | boolean
  | null
  | { [key: string]: Json | undefined }
  | Json[]

export type Database = {
  // Allows to automatically instantiate createClient with right options
  // instead of createClient<Database, { PostgrestVersion: 'XX' }>(URL, KEY)
  __InternalSupabase: {
    PostgrestVersion: "13.0.5"
  }
  public: {
    Tables: {
      advantages: {
        Row: {
          coin_cost: number
          company_id: string
          created_at: string | null
          current_redemptions: number | null
          description: string
          id: string
          is_active: boolean | null
          max_redemptions: number | null
          photo_url: string | null
          title: string
        }
        Insert: {
          coin_cost: number
          company_id: string
          created_at?: string | null
          current_redemptions?: number | null
          description: string
          id?: string
          is_active?: boolean | null
          max_redemptions?: number | null
          photo_url?: string | null
          title: string
        }
        Update: {
          coin_cost?: number
          company_id?: string
          created_at?: string | null
          current_redemptions?: number | null
          description?: string
          id?: string
          is_active?: boolean | null
          max_redemptions?: number | null
          photo_url?: string | null
          title?: string
        }
        Relationships: [
          {
            foreignKeyName: "advantages_company_id_fkey"
            columns: ["company_id"]
            isOneToOne: false
            referencedRelation: "companies"
            referencedColumns: ["id"]
          },
        ]
      }
      companies: {
        Row: {
          cnpj: string
          company_name: string
          created_at: string | null
          id: string
        }
        Insert: {
          cnpj: string
          company_name: string
          created_at?: string | null
          id: string
        }
        Update: {
          cnpj?: string
          company_name?: string
          created_at?: string | null
          id?: string
        }
        Relationships: [
          {
            foreignKeyName: "companies_id_fkey"
            columns: ["id"]
            isOneToOne: true
            referencedRelation: "profiles"
            referencedColumns: ["id"]
          },
        ]
      }
      institutions: {
        Row: {
          created_at: string | null
          id: string
          name: string
        }
        Insert: {
          created_at?: string | null
          id?: string
          name: string
        }
        Update: {
          created_at?: string | null
          id?: string
          name?: string
        }
        Relationships: []
      }
      professors: {
        Row: {
          coin_balance: number | null
          cpf: string
          created_at: string | null
          department: string
          id: string
          institution_id: string
          last_coin_refresh: string | null
          name: string
        }
        Insert: {
          coin_balance?: number | null
          cpf: string
          created_at?: string | null
          department: string
          id: string
          institution_id: string
          last_coin_refresh?: string | null
          name: string
        }
        Update: {
          coin_balance?: number | null
          cpf?: string
          created_at?: string | null
          department?: string
          id?: string
          institution_id?: string
          last_coin_refresh?: string | null
          name?: string
        }
        Relationships: [
          {
            foreignKeyName: "professors_id_fkey"
            columns: ["id"]
            isOneToOne: true
            referencedRelation: "profiles"
            referencedColumns: ["id"]
          },
          {
            foreignKeyName: "professors_institution_id_fkey"
            columns: ["institution_id"]
            isOneToOne: false
            referencedRelation: "institutions"
            referencedColumns: ["id"]
          },
        ]
      }
      profiles: {
        Row: {
          created_at: string | null
          email: string
          id: string
          user_type: Database["public"]["Enums"]["user_type"]
        }
        Insert: {
          created_at?: string | null
          email: string
          id: string
          user_type: Database["public"]["Enums"]["user_type"]
        }
        Update: {
          created_at?: string | null
          email?: string
          id?: string
          user_type?: Database["public"]["Enums"]["user_type"]
        }
        Relationships: []
      }
      redemptions: {
        Row: {
          advantage_id: string
          coupon_code: string
          created_at: string | null
          id: string
          student_id: string
        }
        Insert: {
          advantage_id: string
          coupon_code: string
          created_at?: string | null
          id?: string
          student_id: string
        }
        Update: {
          advantage_id?: string
          coupon_code?: string
          created_at?: string | null
          id?: string
          student_id?: string
        }
        Relationships: [
          {
            foreignKeyName: "redemptions_advantage_id_fkey"
            columns: ["advantage_id"]
            isOneToOne: false
            referencedRelation: "advantages"
            referencedColumns: ["id"]
          },
          {
            foreignKeyName: "redemptions_student_id_fkey"
            columns: ["student_id"]
            isOneToOne: false
            referencedRelation: "students"
            referencedColumns: ["id"]
          },
        ]
      }
      students: {
        Row: {
          address: string
          coin_balance: number | null
          course: string
          cpf: string
          created_at: string | null
          id: string
          institution_id: string
          name: string
          rg: string
        }
        Insert: {
          address: string
          coin_balance?: number | null
          course: string
          cpf: string
          created_at?: string | null
          id: string
          institution_id: string
          name: string
          rg: string
        }
        Update: {
          address?: string
          coin_balance?: number | null
          course?: string
          cpf?: string
          created_at?: string | null
          id?: string
          institution_id?: string
          name?: string
          rg?: string
        }
        Relationships: [
          {
            foreignKeyName: "students_id_fkey"
            columns: ["id"]
            isOneToOne: true
            referencedRelation: "profiles"
            referencedColumns: ["id"]
          },
          {
            foreignKeyName: "students_institution_id_fkey"
            columns: ["institution_id"]
            isOneToOne: false
            referencedRelation: "institutions"
            referencedColumns: ["id"]
          },
        ]
      }
      transactions: {
        Row: {
          amount: number
          created_at: string | null
          id: string
          professor_id: string
          reason: string
          student_id: string
        }
        Insert: {
          amount: number
          created_at?: string | null
          id?: string
          professor_id: string
          reason: string
          student_id: string
        }
        Update: {
          amount?: number
          created_at?: string | null
          id?: string
          professor_id?: string
          reason?: string
          student_id?: string
        }
        Relationships: [
          {
            foreignKeyName: "transactions_professor_id_fkey"
            columns: ["professor_id"]
            isOneToOne: false
            referencedRelation: "professors"
            referencedColumns: ["id"]
          },
          {
            foreignKeyName: "transactions_student_id_fkey"
            columns: ["student_id"]
            isOneToOne: false
            referencedRelation: "students"
            referencedColumns: ["id"]
          },
        ]
      }
    }
    Views: {
      [_ in never]: never
    }
    Functions: {
      generate_coupon_code: {
        Args: Record<PropertyKey, never>
        Returns: string
      }
    }
    Enums: {
      user_type: "student" | "professor" | "company"
    }
    CompositeTypes: {
      [_ in never]: never
    }
  }
}

type DatabaseWithoutInternals = Omit<Database, "__InternalSupabase">

type DefaultSchema = DatabaseWithoutInternals[Extract<keyof Database, "public">]

export type Tables<
  DefaultSchemaTableNameOrOptions extends
    | keyof (DefaultSchema["Tables"] & DefaultSchema["Views"])
    | { schema: keyof DatabaseWithoutInternals },
  TableName extends DefaultSchemaTableNameOrOptions extends {
    schema: keyof DatabaseWithoutInternals
  }
    ? keyof (DatabaseWithoutInternals[DefaultSchemaTableNameOrOptions["schema"]]["Tables"] &
        DatabaseWithoutInternals[DefaultSchemaTableNameOrOptions["schema"]]["Views"])
    : never = never,
> = DefaultSchemaTableNameOrOptions extends {
  schema: keyof DatabaseWithoutInternals
}
  ? (DatabaseWithoutInternals[DefaultSchemaTableNameOrOptions["schema"]]["Tables"] &
      DatabaseWithoutInternals[DefaultSchemaTableNameOrOptions["schema"]]["Views"])[TableName] extends {
      Row: infer R
    }
    ? R
    : never
  : DefaultSchemaTableNameOrOptions extends keyof (DefaultSchema["Tables"] &
        DefaultSchema["Views"])
    ? (DefaultSchema["Tables"] &
        DefaultSchema["Views"])[DefaultSchemaTableNameOrOptions] extends {
        Row: infer R
      }
      ? R
      : never
    : never

export type TablesInsert<
  DefaultSchemaTableNameOrOptions extends
    | keyof DefaultSchema["Tables"]
    | { schema: keyof DatabaseWithoutInternals },
  TableName extends DefaultSchemaTableNameOrOptions extends {
    schema: keyof DatabaseWithoutInternals
  }
    ? keyof DatabaseWithoutInternals[DefaultSchemaTableNameOrOptions["schema"]]["Tables"]
    : never = never,
> = DefaultSchemaTableNameOrOptions extends {
  schema: keyof DatabaseWithoutInternals
}
  ? DatabaseWithoutInternals[DefaultSchemaTableNameOrOptions["schema"]]["Tables"][TableName] extends {
      Insert: infer I
    }
    ? I
    : never
  : DefaultSchemaTableNameOrOptions extends keyof DefaultSchema["Tables"]
    ? DefaultSchema["Tables"][DefaultSchemaTableNameOrOptions] extends {
        Insert: infer I
      }
      ? I
      : never
    : never

export type TablesUpdate<
  DefaultSchemaTableNameOrOptions extends
    | keyof DefaultSchema["Tables"]
    | { schema: keyof DatabaseWithoutInternals },
  TableName extends DefaultSchemaTableNameOrOptions extends {
    schema: keyof DatabaseWithoutInternals
  }
    ? keyof DatabaseWithoutInternals[DefaultSchemaTableNameOrOptions["schema"]]["Tables"]
    : never = never,
> = DefaultSchemaTableNameOrOptions extends {
  schema: keyof DatabaseWithoutInternals
}
  ? DatabaseWithoutInternals[DefaultSchemaTableNameOrOptions["schema"]]["Tables"][TableName] extends {
      Update: infer U
    }
    ? U
    : never
  : DefaultSchemaTableNameOrOptions extends keyof DefaultSchema["Tables"]
    ? DefaultSchema["Tables"][DefaultSchemaTableNameOrOptions] extends {
        Update: infer U
      }
      ? U
      : never
    : never

export type Enums<
  DefaultSchemaEnumNameOrOptions extends
    | keyof DefaultSchema["Enums"]
    | { schema: keyof DatabaseWithoutInternals },
  EnumName extends DefaultSchemaEnumNameOrOptions extends {
    schema: keyof DatabaseWithoutInternals
  }
    ? keyof DatabaseWithoutInternals[DefaultSchemaEnumNameOrOptions["schema"]]["Enums"]
    : never = never,
> = DefaultSchemaEnumNameOrOptions extends {
  schema: keyof DatabaseWithoutInternals
}
  ? DatabaseWithoutInternals[DefaultSchemaEnumNameOrOptions["schema"]]["Enums"][EnumName]
  : DefaultSchemaEnumNameOrOptions extends keyof DefaultSchema["Enums"]
    ? DefaultSchema["Enums"][DefaultSchemaEnumNameOrOptions]
    : never

export type CompositeTypes<
  PublicCompositeTypeNameOrOptions extends
    | keyof DefaultSchema["CompositeTypes"]
    | { schema: keyof DatabaseWithoutInternals },
  CompositeTypeName extends PublicCompositeTypeNameOrOptions extends {
    schema: keyof DatabaseWithoutInternals
  }
    ? keyof DatabaseWithoutInternals[PublicCompositeTypeNameOrOptions["schema"]]["CompositeTypes"]
    : never = never,
> = PublicCompositeTypeNameOrOptions extends {
  schema: keyof DatabaseWithoutInternals
}
  ? DatabaseWithoutInternals[PublicCompositeTypeNameOrOptions["schema"]]["CompositeTypes"][CompositeTypeName]
  : PublicCompositeTypeNameOrOptions extends keyof DefaultSchema["CompositeTypes"]
    ? DefaultSchema["CompositeTypes"][PublicCompositeTypeNameOrOptions]
    : never

export const Constants = {
  public: {
    Enums: {
      user_type: ["student", "professor", "company"],
    },
  },
} as const
