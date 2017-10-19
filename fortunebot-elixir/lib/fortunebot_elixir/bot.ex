defmodule Fortunebot.Bot do

  def auth(code) do
    "https://slack.com/api/oauth.access" <> 
    "?code=#{code}&client_id=#{System.get_env("CLIENT_ID")}&client_secret=#{System.get_env("CLIENT_SECRET")}"
    |> HTTPoison.get
    |> handle_oauth_access_response
  end

  defp handle_oauth_access_response({:ok, %HTTPoison.Response{body: body}}) do
    case Poison.Parser.parse(body, keys: :atoms) do
      {:ok, %{ok: true} = json} -> {:ok, json}
      {:ok, %{ok: false, error: reason}} -> {:error, reason}
      {:error, _} -> {:error, "Error parsing body"}
    end
  end

  defp handle_oauth_access_response(error), do: error
end