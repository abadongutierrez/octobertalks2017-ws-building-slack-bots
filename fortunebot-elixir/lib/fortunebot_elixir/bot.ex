defmodule Fortunebot.Bot do

  def auth(code) do
    # TODO: call GET "https://slack.com/api/oauth.access"
    # TIP: Use HTTPoison.get
    # TIP: You can use handle_oauth_access_response function to handle the response  HTTPoison.get
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