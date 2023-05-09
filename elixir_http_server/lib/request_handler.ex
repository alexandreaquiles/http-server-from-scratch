defmodule RequestHandler do
  @moduledoc """
  Module responsible for handling client requests.
  """

  def run(client) do
    Task.start_link(fn ->
      with {:ok, data} <- :gen_tcp.recv(client, 0) do
        request_data = String.split(data, "\r\n")
        start_line = List.first(request_data)

        # process response
        response_data =
          case start_line do
            "GET" <> _rest ->
              "HTTP/1.1 200 OK\r\n\r\nHello World!"
              |> IO.inspect(label: :success)

            _ ->
              "HTTP/1.1 405 Method Not Allowed\r\n"
              |> IO.inspect(label: :failure)
          end

        :gen_tcp.send(client, response_data)
        :gen_tcp.close(client)
        IO.inspect("[*:3000] Closing connection...")
      end
    end)
  end
end
